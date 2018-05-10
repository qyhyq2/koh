package com.koh.config.thrift;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author qianyuhang
 */
@Data
@NoArgsConstructor
public class ThriftServerConfigItem {
    String name;

    int port;

    int threads;

    String dispatcher;
}
