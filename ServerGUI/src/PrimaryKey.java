/*
 * Copyright (c) 2018. Alikhan Mussabekov
 * Gmail: alikhanmussabekov@gmail.com
 */

import java.lang.annotation.*;
import java.lang.annotation.RetentionPolicy;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface PrimaryKey {
}
