package com.example.demo.services;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dtos.AccountDTO;
import com.example.demo.entities.Account;
import com.example.demo.repositories.AccountRepository;
@Service
public class AccountServiceImplement implements AccountService{
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private ModelMapper mapper;
	@Autowired
	private PasswordEncoder encoder;
	
	@Override
	public List<Account> findAll() {
		return mapper.map(accountRepository.findAll(), new TypeToken<List<AccountDTO>>() {}.getType());
	}
	@Override
	public boolean login(String username, String password, boolean status) {
		try {
			Account account = accountRepository.findbyUsername(username);
			if(account != null) {
				return encoder.matches(password, account.getPassword());
			}
			return false;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
	@Override
	public boolean save(AccountDTO accountDTO) {
		try {
			Account account =  mapper.map(accountDTO, Account.class);
			account.setPassword(encoder.encode(accountDTO.getPassword()));
			accountRepository.save(account);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	@Override
	public AccountDTO findByUsername(String username) {
		// TODO Auto-generated method stub
		return mapper.map(accountRepository.findbyUsername(username), new AccountDTO().getClass());
	}
	@Override
	public AccountDTO findByEmail(String email) {
		// TODO Auto-generated method stub
		return mapper.map(accountRepository.findbyEmail(email), new AccountDTO().getClass());
	}
	@Override
	public Account findByID(int id) {
		return accountRepository.findById(id).get();
	}

}
