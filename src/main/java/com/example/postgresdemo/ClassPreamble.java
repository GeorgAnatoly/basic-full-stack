package com.example.postgresdemo;

import java.lang.annotation.Documented;

@Documented
public @interface ClassPreamble {
    String author();
    String date();
    int currentRevision() default 1;
    String lastModified() default "n/a";
    String lastModifiedBy() default "n/a";
    String[] reviewers();
}
