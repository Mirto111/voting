package ru.projects.voting;

import static java.util.Objects.requireNonNull;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.projects.voting.model.User;

public class AuthorizedUser extends org.springframework.security.core.userdetails.User {

  private static final long serialVersionUID = 1L;

  private final User user;


  public AuthorizedUser(User user) {
    super(user.getEmail(), user.getPassword(), true, true, true, true, user.getRoles());
    this.user = new User(user.getId(), user.getEmail(), user.getName(), user.getPassword(),
        user.getRoles());
  }

  public static int id() {
    return get().user.getId();
  }

  public static AuthorizedUser safeGet() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth == null) {
      return null;
    }
    Object principal = auth.getPrincipal();
    return (principal instanceof AuthorizedUser) ? (AuthorizedUser) principal : null;
  }

  public static AuthorizedUser get() {
    AuthorizedUser user = safeGet();
    requireNonNull(user, "No authorized user found");
    return user;
  }

  public User getUser() {
    return user;
  }

}
