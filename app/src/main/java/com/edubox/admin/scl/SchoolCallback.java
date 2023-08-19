package com.edubox.admin.scl;

public interface SchoolCallback {
    void onSchoolRetrieved(School school);
    void onSchoolNotFound();
}
