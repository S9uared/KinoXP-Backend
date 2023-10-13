package dat3.kinoxp.configuration;

import dat3.kinoxp.dto.TheaterRequest;
import dat3.kinoxp.entity.Theater;
import dat3.kinoxp.repository.MovieRepository;
import dat3.kinoxp.repository.TheaterRepository;
import dat3.kinoxp.service.TheaterService;
import dat3.security.entity.Role;
import dat3.security.entity.UserWithRoles;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import dat3.security.repository.UserWithRolesRepository;

//@Controller
//public class DeveloperData implements ApplicationRunner {}