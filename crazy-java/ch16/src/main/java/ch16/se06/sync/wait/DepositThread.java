package ch16.se06.sync.wait;

import java.math.BigDecimal;

// 存款线程
public class DepositThread extends Thread {

    // 要操作的账户
    private Account account;
    // 每次存入的钱数目
    private BigDecimal amount;

    public DepositThread(String threadName, Account account, BigDecimal amount) {
        super(threadName);
        this.account = account;
        this.amount = amount;
    }

    @Override
    public void run() {
        int i = 0;
        for (; i < 5; i++) {
            account.deposit(amount);
        }
        System.out.println("取款：" + i);
    }

}
