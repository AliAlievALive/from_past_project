FROM quay.io/keycloak/keycloak:23.0.1

# Переключаемся на root для установки пакетов
USER root

# Устанавливаем curl
RUN dnf install -y curl && dnf clean all

# Возвращаемся к пользователю Keycloak
USER 1000

# Устанавливаем стандартные команды запуска Keycloak
ENTRYPOINT ["/opt/keycloak/bin/kc.sh"]
CMD ["start-dev", "--import-realm", "--features=admin-fine-grained-authz"]