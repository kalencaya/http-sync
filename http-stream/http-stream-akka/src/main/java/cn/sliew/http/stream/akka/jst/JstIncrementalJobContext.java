package cn.sliew.http.stream.akka.jst;

import akka.actor.typed.ActorSystem;
import cn.sliew.http.stream.akka.framework.Parallel;
import cn.sliew.http.stream.akka.framework.SyncOffsetJobContext;
import cn.sliew.http.stream.akka.framework.SyncOffsetManager;
import cn.sliew.http.stream.akka.framework.base.AbstractJobContext;
import cn.sliew.http.stream.common.util.DateUtil;
import cn.sliew.http.stream.dao.entity.job.JobAuthorization;
import cn.sliew.http.stream.dao.entity.job.JobInfo;
import io.micrometer.core.instrument.MeterRegistry;

import java.util.Properties;

public abstract class JstIncrementalJobContext
        extends AbstractJobContext<JstIncrementalRootTask, JstIncrementalSubTask>
        implements SyncOffsetJobContext<JstIncrementalRootTask, JstIncrementalSubTask>, Parallel {

    private final SyncOffsetManager syncOffsetManager;

    public JstIncrementalJobContext(Long jobId, Long jobInstanceId, JobInfo job, JobAuthorization authorization, Properties properties, MeterRegistry meterRegistry, ActorSystem actorSystem, SyncOffsetManager syncOffsetManager) {
        super(jobId, jobInstanceId, job, authorization, properties, meterRegistry, actorSystem);
        this.syncOffsetManager = syncOffsetManager;
    }

    @Override
    public SyncOffsetManager getSyncOffsetManager() {
        return syncOffsetManager;
    }

    @Override
    public String getFinalSyncOffset() {
        return DateUtil.formatDateTime(DateUtil.lastHour());
    }
}
