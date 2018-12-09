package vn.com.upload.services;

import vn.com.upload.viewmodels.Intent;

import java.util.List;

/**
 * @author NhanVT3
 */
public interface IntentService {
    Intent saveIntent(Intent intent);
    List<Intent> findAll();
    Intent findByName(String name);
}
