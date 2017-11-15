package com.jamieswhiteshirt.wmtc.api;

import java.util.List;
import java.util.Set;

public interface IChunkInfo {
    Set<List<StackTraceElement>> getStackTraces();

    void setStackTraces(Set<List<StackTraceElement>> stackTraces);

    void addStackTrace(List<StackTraceElement> stackTrace);
}
