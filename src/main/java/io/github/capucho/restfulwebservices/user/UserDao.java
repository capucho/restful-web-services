package io.github.capucho.restfulwebservices.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

@Component
public class UserDao {

	private static List<User> users = new ArrayList<User>();
	
	private static int usersCount = 3;
	
	static {
		users.add(new User(1, "John", new Date()));
		users.add(new User(2, "Eve", new Date()));
		users.add(new User(3, "Agatha", new Date()));
	}
	
	public List<User> findAll() {
		return users;
	}
	
	public User save(User user) {
		if(user.getId() == null) {
			user.setId(++usersCount);
		}
		users.add(user);
		return user;
	}
	
	public User findUser(Integer id) {
		Optional<User> findFirst = users.stream().filter(u -> u.getId().equals(id)).findFirst();
		if(findFirst.isPresent()) {
			return findFirst.get();
		}
		return null;
	}
	
}
