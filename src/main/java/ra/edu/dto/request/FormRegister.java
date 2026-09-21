package ra.edu.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormRegister {
    private String username;
    private String password;
    private String email;
    private String role;
}
