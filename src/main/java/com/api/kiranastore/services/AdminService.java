package com.api.kiranastore.services;

import com.api.kiranastore.entities.Transactions;
import com.api.kiranastore.entities.Users;
import com.api.kiranastore.repo.TransactionsRepo;
import com.api.kiranastore.repo.UsersRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    private final UsersRepo usersRepo;
    private final TransactionsRepo transactionsRepo;
    private final PasswordEncoder passwordEncoder;

    AdminService(UsersRepo userRepo, TransactionsRepo transactionsRepo, PasswordEncoder passwordEncoder){
        this.transactionsRepo = transactionsRepo;
        this.usersRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    /** Add new user to the database
     * @param users - user info
     */
    public void addUser(Users users){
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        usersRepo.save(users);
    }

    /** List of all the users
     * @return all the users
     */
    public List<Users> getAllUsers(){
        return usersRepo.findAll();
    }

    /** List of all the transactions
     * @return all the transactions
     */
    public List<Transactions> getAllTrans(){return transactionsRepo.findAll();}
}
