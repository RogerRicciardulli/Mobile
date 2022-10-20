package com.example.mobile.user;

import android.content.Context;

import com.example.mobile.helper.DBHelper;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public class UserManager {

    private static UserManager instancia;
    Dao<User, Integer> dao;

    public UserManager() {
    }

    public UserManager(Context context) {
        OrmLiteSqliteOpenHelper helper = OpenHelperManager.getHelper(context, DBHelper.class);
        try {
            dao = helper.getDao(User.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static UserManager getInstance(Context context) {
        if(instancia == null){
            instancia = new UserManager(context);
        }
        return instancia;
    }

    public Dao<User, Integer> getDao() {
        return dao;
    }

    public void setDao(Dao<User, Integer> dao) {
        this.dao = dao;
    }

    public List<User> getUsuarios() throws Exception {
        return dao.queryForAll();
    }

    public void registrarUsuario(User user) throws Exception {
        dao.create(user);
    }

}
