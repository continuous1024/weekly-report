package com.huanyu.weekly.event;

import lombok.*;

/**
 * @author arron
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class SimpleEvent {
    private String message;
}
