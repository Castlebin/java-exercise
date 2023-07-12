package T20230712.蔚来;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 两个账户之间转账，要求安全高效。（多线程下）
 *
 * 下面是账户类的定义，只给了这个
 */
class Account {
    private long id;
    private long balance;

    public Account(long id, long balance) {
        this.id = id;
        this.balance = balance;
    }

    public long getId() {
        return id;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }
}

/**
 * 提出假设：假设我们是在单机环境下，不考虑分布式的情况。（分布式环境得使用分布式锁，其实实现更简单些）
 *
 * 如果对象都是新建的，那么，使用 synchronized 关键字，是没有办法实现的。因为锁的是对象。
 * （如果对转账类加锁，那么，就是串行的了。根本不支持多线程同时对多个账户转账。）
 *
 * 因此，必须有限制，只能使用某个统一的外部的依赖，来实现锁的功能。这里我们想到使用一个 ConcurrentHashMap 来实现。(相当于把它当成分布式锁的中心了)。
 * 而且，所有的转账，都必须使用这个统一的转账方法才行（或者这个统一的 ConcurrentHashMap 作为锁的存放的地方）
 */
class TransferCenter {
    // 用于存放锁的地方（相当于分布式锁中间件了）
    private static ConcurrentHashMap<Long, Object> locks = new ConcurrentHashMap<>();

    // 账户 a 向账户 b 转账 balance 的金额
    public static void transfer(Account a, Account b, long amount) {
        if (a.getId() == b.getId()) {
            return;
        }
        Long lockId1 = a.getId() > b.getId() ? a.getId() : b.getId();
        Long lockId2 = a.getId() > b.getId() ? b.getId() : a.getId();
        if (locks.putIfAbsent(lockId1, lockId1) == lockId1) {
            if (locks.putIfAbsent(lockId2, lockId2) == lockId2) {
                // 转账
                a.setBalance(a.getBalance() - amount);
                b.setBalance(b.getBalance() + amount);

                // 释放锁
                locks.remove(lockId2);
            }
            // 释放锁
            locks.remove(lockId1);
        }
    }

}