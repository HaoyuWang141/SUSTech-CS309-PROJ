//package com.ooad.dormitory.security;
//
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//
//public class UserDetailsServiceImpl implements UserDetailsService {
//
//    // Inject your repository or service to retrieve user details
//    // For simplicity, let's assume you have a UserRepository
//
//    // private final UserRepository userRepository;
//
//    // @Autowired
//    // public CustomUserDetailsService(UserRepository userRepository) {
//    //     this.userRepository = userRepository;
//    // }
//
//    @Override
//    public UserDetails loadUserByUsername(String studentId) throws UsernameNotFoundException {
//        // Retrieve user details from your repository or service
//        // Example: User user = userRepository.findByStudentId(studentId);
//
//        // For simplicity, create a UserDetails object (replace with your logic)
//        // UserDetails userDetails = new User(user.getUsername(), user.getPassword(), getAuthorities());
//
//        // return userDetails;
//
//        // Replace the above block with your actual logic to load user details
//        throw new UsernameNotFoundException("User not found with studentId: " + studentId);
//    }
//
//    // Implement logic to retrieve authorities (roles) for the user
//    // private Set<GrantedAuthority> getAuthorities() {
//    //     // Your logic to retrieve authorities
//    // }
//}
//
