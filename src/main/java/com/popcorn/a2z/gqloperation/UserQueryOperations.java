package com.popcorn.a2z.gqloperation;

import com.popcorn.a2z.domain.response.UserDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserQueryOperations {
    private List<UserDTO> getAllUsers;
    private UserDTO getUserByUserId;
    private UserDTO searchUsers;
}
