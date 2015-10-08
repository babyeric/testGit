package com.practice.user;

import com.practice.reverseLookup.ReverseLookupTables;
import com.practice.reverseLookup.StringToLongLookupMapper;
import com.practice.site.SiteDB;
import com.practice.utils.MapperUtils;
import org.apache.commons.lang.StringUtils;
import org.juric.sharding.annotation.ShardParam;

/**
 * Created with IntelliJ IDEA.
 * User: EricChen
 * Date: 10/7/15
 * Time: 4:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserMapperImpl implements UserMapper {
    private UserMapper impl;
    private StringToLongLookupMapper stringToLongLookupMapper;

    public void setUserMapper(UserMapper impl) {
        this.impl = impl;
    }

    public void setStringToLongLookupMapper(StringToLongLookupMapper stringToLongLookupMapper) {
        this.stringToLongLookupMapper = stringToLongLookupMapper;
    }

    @Override
    public int update(@ShardParam("userDB") UserDB userDB) {
        UserDB oldOne = null;
        if (userDB.getEmail() != null) {
            oldOne = impl.getById(userDB.getUserId());
        }
        if (oldOne == null) {
            return 0;
        }
        int ret = impl.update(userDB);

        if (ret > 0 && MapperUtils.keyUpdated(oldOne.getEmail(), userDB.getEmail())) {
            stringToLongLookupMapper.delete(oldOne.getEmail(), oldOne.getUserId(), ReverseLookupTables.USER_EMAIL_TO_ID_LOOKUP);
            stringToLongLookupMapper.insert(userDB.getEmail(), userDB.getUserId(), ReverseLookupTables.USER_EMAIL_TO_ID_LOOKUP);
        }

        return ret;
    }

    @Override
    public int insert(@ShardParam("userDB") UserDB userDB) {
        int ret = impl.insert(userDB);
        if (ret > 0) {
            stringToLongLookupMapper.insert(userDB.getEmail(), userDB.getUserId(), ReverseLookupTables.USER_EMAIL_TO_ID_LOOKUP);
        }

        return ret;
    }

    @Override
    public UserDB getById(@ShardParam("userId") Long userId) {
        return impl.getById(userId);
    }

    @Override
    public UserDB getByEmail(@ShardParam("email") String email) {
        return impl.getByEmail(email);
    }
}
