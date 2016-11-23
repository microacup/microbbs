package me.micro.bbs.security.support;

import me.micro.bbs.security.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * RoleService
 *
 * Created by microacup on 2016/11/23.
 */
@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    List<Role> findAll() {
        return roleRepository.findAll();
    }

    public void deleteById(Long id) {
        roleRepository.delete(id);
    }

    public Role findById(Long id) {
        return roleRepository.findOne(id);
    }

    public void save(Role role) {
        roleRepository.save(role);
    }
}
