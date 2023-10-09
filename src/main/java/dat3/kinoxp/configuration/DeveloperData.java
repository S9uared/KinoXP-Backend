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

@Controller
public class DeveloperData implements ApplicationRunner {

    TheaterRepository theaterRepository;
    TheaterService theaterService;
    MovieRepository movieRepository;
    UserWithRolesRepository userWithRolesRepository;
    PasswordEncoder passwordEncoder;
    String passwordUsedByAll;

    public DeveloperData(UserWithRolesRepository userWithRolesRepository, PasswordEncoder passwordEncoder, MovieRepository movieRepository, TheaterRepository theaterRepository) {
        this.userWithRolesRepository = userWithRolesRepository;
        this.passwordEncoder = passwordEncoder;
        passwordUsedByAll = "test12";
        this.theaterRepository = theaterRepository;
        this.movieRepository = movieRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        setupUserWithRoleUsers();
        theaterService.createTheater(TheaterRequest.builder().
                id(1).rows(25).seatsPerRow(16).
                build());
        Theater theater2 = new Theater(2, 20, 12);
        theaterRepository.save(theater2);
        movieRepository.saveAll(MovieTestDataFactory.generateTestMovies());
    }

     /*****************************************************************************************
     IMPORTANT!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     NEVER  COMMIT/PUSH CODE WITH DEFAULT CREDENTIALS FOR REAL
     iT'S ONE OF THE TOP SECURITY FLAWS YOU CAN DO

     If you see the lines below in log-outputs on Azure, forget whatever had your attention on, AND FIX THIS PROBLEM

     *****************************************************************************************/
    private void setupUserWithRoleUsers() {
        System.out.println("******************************************************************************");
        System.out.println("********** IMPORTANT!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! ************");
        System.out.println();
        System.out.println("******* NEVER  COMMIT/PUSH CODE WITH DEFAULT CREDENTIALS FOR REAL ************");
        System.out.println("******* REMOVE THIS BEFORE DEPLOYMENT, AND SETUP DEFAULT USERS DIRECTLY  *****");
        System.out.println("**** ** ON YOUR REMOTE DATABASE                 ******************************");
        System.out.println();
        System.out.println("******************************************************************************");
        UserWithRoles user1 = new UserWithRoles("user1", passwordUsedByAll, "user1@a.dk");
        UserWithRoles user2 = new UserWithRoles("user2", passwordUsedByAll, "user2@a.dk");
        UserWithRoles user3 = new UserWithRoles("user3", passwordUsedByAll, "user3@a.dk");
        UserWithRoles user4 = new UserWithRoles("user4", passwordUsedByAll, "user4@a.dk");
        user1.addRole(Role.USER);
        user1.addRole(Role.ADMIN);
        user2.addRole(Role.USER);
        user3.addRole(Role.ADMIN);
        userWithRolesRepository.save(user1);
        userWithRolesRepository.save(user2);
        userWithRolesRepository.save(user3);
        userWithRolesRepository.save(user4);
    }
}
