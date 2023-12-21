package io.udpn.commonsjob.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface JobParam {

  /**
   * job handler
   */
  String handler();

  /**
   * schedule type Can be selected from ScheduleType class
   */
  String scheduleType();

  /**
   * If scheduleType is CRON, you need to fill in the CRON expression. If scheduleType is FIX_RATE,
   * you need to fill in the seconds of int value.
   */
  String scheduleConf();

  /**
   * author
   */
  String author();

  /**
   * job desc
   */
  String jobDesc();

  /**
   * alarm email
   */
  String alarmEmail() default "";

  /**
   * route strategy Can be selected from RouteStrategy class
   */
  String routeStrategy() default "FIRST";

  /**
   * expiration strategy Can be selected from ExpirationStrategy class
   */
  String expirationStrategy() default "DO_NOTHING";

  /**
   * blocking strategy Can be selected from BlockingStrategy class
   */
  String blockingStrategy() default "SERIAL_EXECUTION";

  /**
   * timeout second
   */
  int timeoutSecond() default 0;

  /**
   * fail retry count
   */
  int failRetryCount() default 0;

  /**
   * executor Param
   */
  String executorParam() default "";

  /**
   * child job id
   */
  String childJobId() default "";
}
