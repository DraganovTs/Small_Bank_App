package com.techbank.account.cmd.api.commands;

import com.techbank.account.cmd.domain.AccountAggregate;
import com.techbank.cqrs.core.handlers.EventSourceHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountCommandHandler implements CommandHandler {
    @Autowired
    private EventSourceHandler<AccountAggregate> eventSourceHandler;

    @Override
    public void handle(OpenAccountCommand command) {
        var aggregate = new AccountAggregate(command);
        eventSourceHandler.save(aggregate);
    }

    @Override
    public void handle(DepositFoundCommand command) {
        var aggregate = eventSourceHandler.getById(command.getId());
        aggregate.depositFund(command.getAmount());
        eventSourceHandler.save(aggregate);
    }

    @Override
    public void handle(WithdrawFoundCommand command) {
        var aggregate = eventSourceHandler.getById(command.getId());
        if (command.getAmount() > aggregate.getBalance()) {
            throw new IllegalStateException("Withdraw declined , insufficient funds!");
        }
        aggregate.withdrawFunds(command.getAmount());
        eventSourceHandler.save(aggregate);

    }

    @Override
    public void handle(CloseAccountCommand command) {
        var aggregate = eventSourceHandler.getById(command.getId());
        aggregate.closedAccount();
        eventSourceHandler.save(aggregate);
    }
}
