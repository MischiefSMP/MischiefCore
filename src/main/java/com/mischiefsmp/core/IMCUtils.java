package com.mischiefsmp.core;

import com.mischiefsmp.core.utils.UsernameInfo;

import java.util.ArrayList;
import java.util.UUID;

public interface IMCUtils {
    UUID UUIDFromString(String uuid);
    ArrayList<UsernameInfo> getUsernameInfo(UUID uuid);
    UUID getUserUUID(String username);
}
