package com.techbank.account.cmd.api.commands;

public interface CommandHandler {
    void handle(OpenAccountCommand command);
    void handle(DepositFoundCommand command);
    void handle(WithdrawFoundCommand command);
    void handle(CloseAccountCommand command);
}
