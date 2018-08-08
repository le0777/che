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
if [ -z "$THEIA_PORT" ]; then
    export THEIA_PORT=3000
else
    # Parse THEIA_PORT env var in case it has weird value, such as tcp://10.108.137.206:3000
    theia_port_number_regexp='^[0-9]+$'
    if ! [[ "$THEIA_PORT" =~ $theia_port_number_regexp ]]; then
        # THEIA_PORT contains something other than a number
        theia_port_tcp_uri_regexp='^tcp://[0-9a-zA-Z.]+:[0-9]+$'
        if [[ "$THEIA_PORT" =~ $theia_port_tcp_uri_regexp ]]; then
            # Remove tcp://....: prefix
            THEIA_PORT=${THEIA_PORT##*:}
        else
            echo  1>&2 "Environment variable THEIA_PORT contains unexpected value:$THEIA_PORT"
        fi
    fi
fi

yarn theia start /projects --hostname=0.0.0.0 --port=${THEIA_PORT}
