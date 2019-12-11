package br.edu.uftpr;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.edu.uftpr.model.entity.Expense;
import br.edu.uftpr.model.entity.Revenue;
import br.edu.uftpr.model.entity.User;
import br.edu.uftpr.model.repository.UserRepository;
import br.edu.uftpr.model.service.ExpenseService;
import br.edu.uftpr.model.service.RevenueService;

@SpringBootApplication
public class ApiWeb4Application implements CommandLineRunner {

	@Autowired
	UserRepository userRepository;
	@Autowired
	RevenueService revenueService;
	@Autowired
	ExpenseService expenseService;

	public static void main(String[] args) {
		SpringApplication.run(ApiWeb4Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		List<User> users = new ArrayList<User>();
		users.add(new User(null, "Everton", "everton", "124653"));
		users.add(new User(null, "Luciana", "luciana", "4446465"));
		users.add(new User(null, "Isabela", "isabela", "78787989"));
		users.add(new User(null, "Sheila", "Sheila", "145448559"));
		users.add(new User(null, "Cristhian", "Cristhian", "1545659"));
		users.add(new User(null, "Rafael", "Rafael", "128484859"));
		users.add(new User(null, "Roberto", "Roberto", "154585859"));
		users.add(new User(null, "Robson", "Robson", "15454579"));
		userRepository.saveAll(users);

		List<Revenue> revenues = new ArrayList<Revenue>();
		revenues.add(new Revenue(null, "Teste a Receber 1 ", 59.46, new Date(), "F"));
		revenues.add(new Revenue(null, "Teste a Receber 2 ", 556.55, new Date(), "F"));
		revenues.add(new Revenue(null, "Teste a Receber 3 ", 595.65, new Date(), "T"));
		revenues.add(new Revenue(null, "Teste a Receber 4 ", 593.85, new Date(), "F"));
		revenues.add(new Revenue(null, "Teste a Receber 5 ", 549.45, new Date(), "F"));
		revenues.add(new Revenue(null, "Teste a Receber 6 ", 9.45,   new Date(), "T"));
		revenues.add(new Revenue(null, "Teste a Receber 7 ", 1239.45,new Date(), "F"));
		revenues.add(new Revenue(null, "Teste a RReceber 8 ", 589.55, new Date(), "T"));
		revenues.add(new Revenue(null, "Teste a RReceber 9 ", 139.60, new Date(), "F"));
		revenues.add(new Revenue(null, "Teste a RReceber 10 ", 59.98, new Date(), "F"));
		revenueService.saveAll(revenues);

		List<Expense> expenses = new ArrayList<Expense>();
		expenses.add(new Expense(null, "Teste Contas a Pagar 1 ", 59.46,  new Date(), "F"));
		expenses.add(new Expense(null, "Teste Contas a Pagar 2 ", 556.55, new Date(), "F"));
		expenses.add(new Expense(null, "Teste Contas a Pagar 3 ", 595.65, new Date(), "T"));
		expenses.add(new Expense(null, "Teste Contas a Pagar 4 ", 593.85, new Date(), "F"));
		expenses.add(new Expense(null, "Teste Contas a Pagar 5 ", 549.45, new Date(), "F"));
		expenses.add(new Expense(null, "Teste Contas a Pagar 6 ", 9.45,   new Date(), "T"));
		expenses.add(new Expense(null, "Teste Contas a Pagar 7 ", 1239.45,new Date(), "F"));
		expenses.add(new Expense(null, "Teste Contas a Pagar 8 ", 589.55, new Date(), "T"));
		expenses.add(new Expense(null, "Teste Contas a Pagar 9 ", 139.60, new Date(), "F"));
		expenses.add(new Expense(null, "Teste Contas a Pagar 10 ", 59.98, new Date(), "F"));
		expenseService.saveAll(expenses);
		
	}

}
