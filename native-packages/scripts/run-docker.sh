#!/bin/sh
set -e -u

USER="builder"
HOME="/home/builder"
IMAGE_NAME="xeffyr/android-native-devenv"
CONTAINER_NAME="android-native-devenv"
REPOROOT=$(dirname "$(readlink -f "${0}")")/../

echo "Running container '${CONTAINER_NAME}' from image '${IMAGE_NAME}'..."

sudo docker start "${CONTAINER_NAME}" > /dev/null 2> /dev/null || {
    echo "Creating new container..."

    sudo docker run \
            --detach \
            --env HOME="${HOME}" \
            --env LINES=$(tput lines) \
            --env COLUMNS=$(tput cols) \
            --name "${CONTAINER_NAME}" \
            --volume "${REPOROOT}:${HOME}/env-packages" \
            --tty \
            "${IMAGE_NAME}"

    if [ $(id -u) -ne 1000 -a $(id -u) -ne 0 ]; then
        echo "Changed builder uid/gid... (this may take a while)"
        sudo docker exec --tty "${CONTAINER_NAME}" chown -R $(id -u) "${HOME}"
        sudo docker exec --tty "${CONTAINER_NAME}" chown -R $(id -u) /data
        sudo docker exec --tty "${CONTAINER_NAME}" usermod -u $(id -u) builder
        sudo docker exec --tty "${CONTAINER_NAME}" groupmod -g $(id -g) builder
    fi
}

if [ "$#" -eq  "0" ]; then
    sudo docker exec --interactive --tty --env LINES=$(tput lines) --env COLUMNS=$(tput cols) --user "${USER}" "${CONTAINER_NAME}" bash
else
    sudo docker exec --interactive --tty --env LINES=$(tput lines) --env COLUMNS=$(tput cols) --user "${USER}" "${CONTAINER_NAME}" "${@}"
fi
