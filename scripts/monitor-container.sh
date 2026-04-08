#!/bin/bash

# Check if the user provided a container name parameter
if [ -z "$1" ]; then
  echo "Error: No container name provided."
  echo "Usage: ./monitor-container.sh <container_name>"
  exit 1
fi

CONTAINER_NAME=$1

# Verify the container is running initially so we don't print headers for a dead container
if [ "$(sudo podman inspect -f '{{.State.Running}}' "$CONTAINER_NAME" 2>/dev/null)" != "true" ]; then
  echo "Error: Container '$CONTAINER_NAME' is not currently running."
  exit 1
fi

# Print the Table Header
printf "%-10s | %-8s | %-8s | %-20s | %-20s | %-15s | %-15s\n" "TIME" "CPU %" "MEM %" "NET I/O (Bytes)" "BLOCK I/O" "RX PACKETS" "TX PACKETS"
printf -- "-%.0s" {1..115}; echo ""

while true; do
  NOW=$(date +%T)

  # 1. Attempt to get the container stats (2>&1 captures errors into the variable)
  STATS_OUTPUT=$(sudo podman stats --no-stream --format "{{.CPUPerc}}|{{.MemPerc}}|{{.NetIO}}|{{.BlockIO}}" "$CONTAINER_NAME" 2>&1)
  STATUS_STATS=$?

  # 2. Attempt to get packet counts
  RX=$(sudo podman exec "$CONTAINER_NAME" cat /sys/class/net/eth0/statistics/rx_packets 2>&1)
  STATUS_RX=$?

  TX=$(sudo podman exec "$CONTAINER_NAME" cat /sys/class/net/eth0/statistics/tx_packets 2>&1)
  STATUS_TX=$?

  # If any command failed, break the loop immediately
  if [ $STATUS_STATS -ne 0 ] || [ $STATUS_RX -ne 0 ] || [ $STATUS_TX -ne 0 ] || [ -z "$STATS_OUTPUT" ]; then
    break
  fi

  # 3. Parse the stats output since we know everything succeeded
  IFS='|' read -r CPU MEM NET IOB <<< "$STATS_OUTPUT"

  # 4. Print the formatted row
  printf "%-10s | %-8s | %-8s | %-20s | %-20s | %-15s | %-15s\n" "$NOW" "$CPU" "$MEM" "$NET" "$IOB" "$RX" "$TX"

  # Wait 1 second
  sleep 1
done

echo ""
echo "Container '$CONTAINER_NAME' has stopped. Monitoring finished."
echo ""
echo "--- LAST OUTPUT ---"
echo "STATS_OUTPUT : $STATS_OUTPUT"
echo "RX           : $RX"
echo "TX           : $TX"
