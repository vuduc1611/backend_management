package net.javaguides.springboot.security.services;

import net.javaguides.springboot.Dto.DtoUserChangePass;
import net.javaguides.springboot.Dto.EmailDetails;
import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.model.User;
import net.javaguides.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    PasswordEncoder encoder;

    @Value("${spring.mail.username}") private String sender;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User Not Found with -> username or email : " + username));

        return UserPrinciple.build(user);
    }

    public String sendEmail(String email) {
        if(!userRepository.existsByEmail(email)) {
            return "Not found Username contains this email!";
        }
        try {
            User currentUser = userRepository.findByEmail(email);
            String pass = "123456";
            currentUser.setPassword(encoder.encode(pass));
            userRepository.save(currentUser);

            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setFrom(sender);// sender: mail đăng ký host gửi
            msg.setTo(currentUser.getEmail());// gửi thư đến email cần nhận.
            msg.setSubject("Email relation Forget Password");// tiêu đề
            // nội dung mail
            msg.setText("Hello \n\n" + "Please information below:" + "\n\n"
                    + "Password have changed to:"
                    + "123456"
                    + " \n\n"
                    + "You should login the system and change your password!" + " \n\n"
                    + "Thank for \n" + currentUser.getEmail());
            javaMailSender.send(msg);

            return "Success";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }

    }

    public String changePassword(DtoUserChangePass dtoUserChangePass) {
        User currentUser = userRepository.findByUsername(dtoUserChangePass.getUsername()).orElseThrow(() -> new ResourceNotFoundException("User", "Username", dtoUserChangePass.getUsername()));

        if(dtoUserChangePass.getPassword().equals(dtoUserChangePass.getConfirmPassword())) {
            currentUser.setPassword(encoder.encode(dtoUserChangePass.getPassword()));
            userRepository.save(currentUser);
            return "Success";
        } else {
            return "Fail";
        }
    }

}
