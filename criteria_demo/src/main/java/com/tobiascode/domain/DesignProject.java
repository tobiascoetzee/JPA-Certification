package com.tobiascode.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("DP")
public class DesignProject extends Project {
}
