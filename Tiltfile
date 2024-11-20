allow_k8s_contexts('local')
docker_prune_settings(num_builds=1, keep_recent=1)

aissemble_version = '1.10.0'

build_args = { 'DOCKER_BASELINE_REPO_ID': 'ghcr.io/',
               'VERSION_AISSEMBLE': aissemble_version}

# Kafka
yaml = helm(
    'verify-1-10-deploy/src/main/resources/apps/kafka-cluster',
    values=['verify-1-10-deploy/src/main/resources/apps/kafka-cluster/values.yaml',
        'verify-1-10-deploy/src/main/resources/apps/kafka-cluster/values-dev.yaml']
)
k8s_yaml(yaml)

yaml = helm(
   'verify-1-10-deploy/src/main/resources/apps/spark-infrastructure',
   name='spark-infrastructure',
   values=['verify-1-10-deploy/src/main/resources/apps/spark-infrastructure/values.yaml',
       'verify-1-10-deploy/src/main/resources/apps/spark-infrastructure/values-dev.yaml']
)
k8s_yaml(yaml)
yaml = helm(
   'verify-1-10-deploy/src/main/resources/apps/spark-operator',
   name='spark-operator',
   values=['verify-1-10-deploy/src/main/resources/apps/spark-operator/values.yaml',
       'verify-1-10-deploy/src/main/resources/apps/spark-operator/values-dev.yaml']
)
k8s_yaml(yaml)
yaml = helm(
   'verify-1-10-deploy/src/main/resources/apps/pipeline-invocation-service',
   name='pipeline-invocation-service',
   values=['verify-1-10-deploy/src/main/resources/apps/pipeline-invocation-service/values.yaml',
       'verify-1-10-deploy/src/main/resources/apps/pipeline-invocation-service/values-dev.yaml']
)
k8s_yaml(yaml)
yaml = helm(
   'verify-1-10-deploy/src/main/resources/apps/policy-decision-point',
   name='policy-decision-point',
   values=['verify-1-10-deploy/src/main/resources/apps/policy-decision-point/values.yaml',
       'verify-1-10-deploy/src/main/resources/apps/policy-decision-point/values-dev.yaml']
)
k8s_yaml(yaml)
k8s_yaml('verify-1-10-deploy/src/main/resources/apps/spark-worker-image/spark-worker-image.yaml')


yaml = helm(
   'verify-1-10-deploy/src/main/resources/apps/s3-local',
   name='s3-local',
   values=['verify-1-10-deploy/src/main/resources/apps/s3-local/values.yaml',
       'verify-1-10-deploy/src/main/resources/apps/s3-local/values-dev.yaml']
)
k8s_yaml(yaml)

k8s_kind('SparkApplication', image_json_path='{.spec.image}')

# spark-worker-image
docker_build(
    ref='verify-1-10-spark-worker-docker',
    context='verify-1-10-docker/verify-1-10-spark-worker-docker',
    build_args=build_args,
    extra_tag='verify-1-10-spark-worker-docker:latest',
    dockerfile='verify-1-10-docker/verify-1-10-spark-worker-docker/src/main/resources/docker/Dockerfile'
)


# policy-decision-point
docker_build(
    ref='verify-1-10-policy-decision-point-docker',
    context='verify-1-10-docker/verify-1-10-policy-decision-point-docker',
    build_args=build_args,
    dockerfile='verify-1-10-docker/verify-1-10-policy-decision-point-docker/src/main/resources/docker/Dockerfile'
)



yaml = local('helm template oci://ghcr.io/boozallen/aissemble-spark-application-chart --version %s --values verify-1-10-pipelines/spark-pipeline/src/main/resources/apps/spark-pipeline-base-values.yaml,verify-1-10-pipelines/spark-pipeline/src/main/resources/apps/spark-pipeline-dev-values.yaml' % aissemble_version)
k8s_yaml(yaml)
k8s_resource('spark-pipeline', port_forwards=[port_forward(4747, 4747, 'debug')], auto_init=False, trigger_mode=TRIGGER_MODE_MANUAL)

# Add deployment resources here