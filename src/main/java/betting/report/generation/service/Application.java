package betting.report.generation.service;

import betting.report.generation.service.task.LiabilityReporter;

public class Application
{
    public static void main(String[] args)
    {
        new LiabilityReporter()
                .handleReporting();
    }
}