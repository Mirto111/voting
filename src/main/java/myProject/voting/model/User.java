package myProject.voting.model;

import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Column(name = "email", nullable = false)
    @Email
    @NotBlank
    private String email;

    @Column(name = "name", nullable = false)
    @NotBlank
    private String name;

    @Column(name = "password", nullable = false)
    @NotBlank
    private String password;

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private Set<Role> roles;

    public User() {
    }


    public User(Integer id, String email, String name, String password, Role role, Role... roles) {
        super(id);
        this.email = email;
        this.name = name;
        this.password = password;
        setRoles(EnumSet.of(role, roles));
    }

    public User(Integer id, String email, String name, String password, Set<Role> roles) {
        super(id);
        this.email = email;
        this.name = name;
        this.password = password;
        setRoles(roles);
    }

    public User(Integer id, String email, String name, String password) {
        super(id);
        this.email = email;
        this.name = name;
        this.password = password;
        setRoles(EnumSet.of(Role.USER));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = CollectionUtils.isEmpty(roles) ? Collections.emptySet() : EnumSet.copyOf(roles);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
