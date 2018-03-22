package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.BankConnection;
import view.WelcomeScreen;

import java.sql.*;

public class DeleteClientAccount implements EventHandler<ActionEvent>
{

    private Connection bankConnection;
    private PreparedStatement preparedStatement;
    private int acccount_number;
    private WelcomeScreen view;

    public static void main(String[] args)
    {
        new DeleteClientAccount();
    }

    public DeleteClientAccount()
    {
        System.out.println("DeleteClientAccount()");
        bankConnection = BankConnection.createConnection();
       // this.view = new WelcomeScreen();
    }

    public DeleteClientAccount(int account_number)
    {
        this.acccount_number = account_number;
        bankConnection = BankConnection.createConnection();
    }

    private void removeClient(int account_number) throws SQLException
    {

        removeCheckingDeposit(account_number);

        removeCheckingWithdrawal(account_number);

        removeCheckingAccount(account_number);

        removeClientFromDatabase(account_number);

        System.out.println("Client removed");

    }

    public void removeCheckingAccount(int account_number) throws SQLException
    {
        String removeClientCK_ACCT = "DELETE FROM checking_account where account_number = ?";

        preparedStatement = bankConnection.prepareStatement(removeClientCK_ACCT);

        preparedStatement.setInt(1, account_number);

        preparedStatement.execute();
    }

    public void removeCheckingDeposit(int account_number) throws SQLException
    {
        String removeClientCK_DEPOSIT = "DELETE FROM checking_deposit where account_number = ?";

        preparedStatement = bankConnection.prepareStatement(removeClientCK_DEPOSIT);

        preparedStatement.setInt(1, account_number);

        preparedStatement.execute();
    }

    public void removeCheckingWithdrawal(int account_number) throws SQLException
    {
        String removeClientCK_WITHDRAWAL = "DELETE FROM checking_withdrawl where account_number = ?";

        preparedStatement = bankConnection.prepareStatement(removeClientCK_WITHDRAWAL);

        preparedStatement.setInt(1, account_number);

        preparedStatement.execute();

    }

    private void removeClientFromDatabase(int account_number) throws SQLException
    {
        String removeClient = "DELETE FROM clients where account_number = ?";

        preparedStatement = bankConnection.prepareStatement(removeClient);

        preparedStatement.setInt(1, account_number);

        preparedStatement.execute();

    }


    public void displayClients()
    {
        try
        {
            Statement statement = bankConnection.createStatement();

            String clientStatement = "SELECT first_name, last_name, account_number FROM clients";

            ResultSet resultSet = statement.executeQuery(clientStatement);

            //   clearResults();

            WelcomeScreen.results.appendText("List of clients in bank, please select one and click submit \n \n");

            while(resultSet.next())
            {
                WelcomeScreen.results.appendText(resultSet.getString(1) + ", " +
                        resultSet.getString(2) + "-" +
                        "Account #: " +
                        resultSet.getString(3) + "\n");
            }

        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }


    @Override
    public void handle(ActionEvent event)
    {
        System.out.println("handle()");
    }
}
