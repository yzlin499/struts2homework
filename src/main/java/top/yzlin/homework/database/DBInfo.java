package top.yzlin.homework.database;

import top.yzlin.homework.ioc.Value;

/**
 * 直接作废
 */
@Deprecated
public class DBInfo {
    public static final String DRIVER="db.driver";
    public static final String JDBC_URL="db.jdbcUrl";
    public static final String USER="db.user";
    public static final String PASSWORD="db.password";
    public static final String POOLS="db.pools";

    private String user;
    private String password;
    private String driver;
    private String jdbcUrl;
    private int pools;


    @Value(POOLS)
    public void setPools(int pools) {
        this.pools = pools;
    }

    @Value(USER)
    public void setUser(String user) {
        this.user = user;
    }

    @Value(PASSWORD)
    public void setPassword(String password) {
        this.password = password;
    }

    @Value(DRIVER)
    public void setDriver(String driver) {
        this.driver = driver;
    }

    @Value(JDBC_URL)
    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getDriver() {
        return driver;
    }


    public int getPools() {
        return pools;
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }
}
