package io.udpn.commonsjob.utils;

/**
 * executor route strategy
 */
public class RouteStrategy {

  final public static String FIRST = "FIRST";

  final public static String LAST = "LAST";

  final public static String ROUND = "ROUND";

  final public static String RANDOM = "RANDOM";

  final public static String CONSISTENT_HASH = "CONSISTENT_HASH";

  final public static String LEAST_FREQUENTLY_USED = "LEAST_FREQUENTLY_USED";

  final public static String LEAST_RECENTLY_USED = "LEAST_RECENTLY_USED";

  final public static String FAILOVER = "FAILOVER";

  final public static String BUSYOVER = "BUSYOVER";

  final public static String SHARDING_BROADCAST = "SHARDING_BROADCAST";

}
