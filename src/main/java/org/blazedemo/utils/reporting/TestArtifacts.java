package org.blazedemo.utils.reporting;

import java.nio.file.Path;

public record TestArtifacts (

            Path video,

            Path screenshot,

            Path pageSource,

            Path logs
    ) {}

