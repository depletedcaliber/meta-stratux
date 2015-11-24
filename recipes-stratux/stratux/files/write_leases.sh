#!/bin/bash

dumpleases -r -f /var/lib/dhcp/dhcpd.leases | grep -v Host | awk '{print $2}' >  /var/run/dhcp_stratux_leases
