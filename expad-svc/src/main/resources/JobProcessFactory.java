
package com.ocularminds.expad.svc.jobs;

import java.util.Date;
import java.util.Properties;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

public class JobProcessFactory {
    private String serviceType;
    private int issuerNo = 1;
    Properties properties;
    Scheduler sched = null;

    private void checkConfiguration() {
        this.properties = new Properties();
        System.out.println("[EXPAD]: Initializing jobs configuration :");
        System.out.println("[EXPAD]:Done initializing jobs configuration.");
    }

    public void shutdown() throws Exception {
        if (this.sched != null) {
            this.sched.shutdown();
        }
    }

    public void scheduleJobs() throws Exception {
        System.out.println("------- Initializing ----------------------");
        this.checkConfiguration();
        StdSchedulerFactory sf = new StdSchedulerFactory();
        this.sched = sf.getScheduler();
        this.sched.deleteJob("stud-file-job", "expad.jobs");
        this.sched.deleteJob("card-file-job", "expad.jobs");
        this.sched.deleteJob("nysc-file-job", "expad.jobs");
        System.out.println("------- Initialization Complete -----------");
        System.out.println("------- Scheduling Jobs -------------------");
        JobDetail studentFileJob = new JobDetail("stud-file-job", "expad.jobs", StudentCardFilePushJob.class);
        SimpleTrigger ST = new SimpleTrigger("data-trigger", null, new Date(), null, SimpleTrigger.REPEAT_INDEFINITELY, 300000);
        JobDetail customerFileJob = new JobDetail("card-file-job", "expad.jobs", NormalCardFilePushJob.class);
        SimpleTrigger CT = new SimpleTrigger("file-trigger", null, new Date(), null, SimpleTrigger.REPEAT_INDEFINITELY, 300000);
        JobDetail nyscFileJob = new JobDetail("nysc-file-job", "expad.jobs", NyscCardFilePushJob.class);
        SimpleTrigger NT = new SimpleTrigger("cost-trigger", null, new Date(), null, SimpleTrigger.REPEAT_INDEFINITELY, 300000);
        this.sched.scheduleJob(customerFileJob, (Trigger)CT);
        this.sched.scheduleJob(studentFileJob, (Trigger)ST);
        this.sched.scheduleJob(nyscFileJob, (Trigger)NT);
        this.sched.start();
        System.out.println("[expad.jobs] started.");
    }

    public static void main(String[] args) throws Exception {
        System.out.println("initialising jobs in service mode...");
        JobProcessFactory jobfactory = new JobProcessFactory();
        jobfactory.scheduleJobs();
        System.out.println("JobProcessFactory started.");
    }
}

