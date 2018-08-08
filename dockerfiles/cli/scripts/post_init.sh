#!/bin/bash
#
# Copyright (c) 2012-2018 Red Hat, Inc.
# This program and the accompanying materials are made
# available under the terms of the Eclipse Public License 2.0
# which is available at https://www.eclipse.org/legal/epl-2.0/
#
# SPDX-License-Identifier: EPL-2.0
#
# Contributors:
#   Red Hat, Inc. - initial API and implementation
#

post_init() {
  GLOBAL_HOST_IP=${GLOBAL_HOST_IP:=$(docker_run --net host ${BOOTSTRAP_IMAGE_CHEIP})}
  DEFAULT_CHE_HOST=$GLOBAL_HOST_IP
  CHE_HOST=${CHE_HOST:-${DEFAULT_CHE_HOST}}
}
