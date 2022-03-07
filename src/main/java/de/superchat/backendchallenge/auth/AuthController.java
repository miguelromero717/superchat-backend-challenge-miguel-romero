package de.superchat.backendchallenge.auth;

import de.superchat.backendchallenge.auth.payload.SignInClientRequest;
import de.superchat.backendchallenge.auth.payload.SignInClientResponse;
import de.superchat.backendchallenge.auth.payload.SignUpClientRequest;
import de.superchat.backendchallenge.auth.services.RoleService;
import de.superchat.backendchallenge.auth.services.impl.UserDetailsImpl;
import de.superchat.backendchallenge.clients.ClientService;
import de.superchat.backendchallenge.shared.domain.Client;
import de.superchat.backendchallenge.shared.domain.Role;
import de.superchat.backendchallenge.shared.enums.ClientStatus;
import de.superchat.backendchallenge.shared.utils.JwtUtils;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AuthenticationManager authenticationManager;
    private final ClientService clientService;
    private final RoleService roleService;
    private final JwtUtils jwtUtils;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, ClientService clientService,
                          RoleService roleService, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.clientService = clientService;
        this.roleService = roleService;
        this.jwtUtils = jwtUtils;
    }

    @Operation(summary = "Sign up a new Client")
    @PostMapping("/clients/sign-up")
    public ResponseEntity<?> signUpClient (@RequestBody @Validated SignUpClientRequest signUpClientRequest) throws Exception {
        logger.info("Sign Up Client endpoint");

        return new ResponseEntity<>(clientService.signUpClient(getClient(signUpClientRequest)), HttpStatus.CREATED);
    }

    @Operation(summary = "Check client credentials to allow access to the system")
    @PostMapping("/clients/sign-in")
    public ResponseEntity<?> signInClient(@RequestBody @Validated SignInClientRequest signInClientRequest) throws Exception {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInClientRequest.getEmail(), signInClientRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        Optional<Role> role = roleService.findRoleByName(roles.get(0));

        if (role.isEmpty())
            throw new Exception("Invalid Client Role");

        return new ResponseEntity<>(new SignInClientResponse(
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getUsername(),
                jwt,
                role.get()
        ), HttpStatus.OK);
    }

    private Client getClient(SignUpClientRequest signUpClientRequest) {
        Client client = new Client();
        client.setName(signUpClientRequest.getName());
        client.setEmail(signUpClientRequest.getEmail());
        client.setStatus(ClientStatus.ACTIVE);

        return client;
    }

}
