### Prometheus

http://localhost:9090/

### Grafana

http://localhost:3000/

### Reference

#### Prometheus Grafana

https://refactorfirst.com/spring-boot-prometheus-grafana

#### Fluentd

https://arnoldgalovics.com/java-multiline-logs-fluentd/

---

### Deploy

#### EFK (Elasticsearch - Fluentd - Kibana)

``kubectl apply -f k8s/efk-stack.yaml``

``kubectl -n kube-logging get services``

To get the external IP, tunnel minikube ``minikube tunnel``, get the external IP, then go to Kibana UI with port 5601

#### Spring java

In Local ``eval $(minikube docker-env)`` first.

``docker build -t fluentd-app:latest .``

``kubectl apply -f k8s/fluentd-java.yaml``