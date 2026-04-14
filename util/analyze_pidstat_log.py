import sys
import os
from collections import defaultdict
from prettytable import PrettyTable

def analyze_pidstat_log(filepath):
    print("=" * 70)
    print(f"Analyzing: {filepath}")
    print("=" * 70)

    if not os.path.isfile(filepath):
        print(f"Error: File '{filepath}' not found!\n")
        return

    in_csw = False
    col_vol_idx = -1
    col_invol_idx = -1
    col_tid_idx = -1
    col_cmd_idx = -1

    per_second = defaultdict(lambda: {'vol': 0.0, 'invol': 0.0})
    per_thread = defaultdict(lambda: {'vol': 0.0, 'invol': 0.0, 'samples': 0, 'cmd': ''})

    with open(filepath, 'r') as file:
        for line_num, line in enumerate(file, 1):
            parts = line.split()
            if not parts:
                continue

            if "%usr" in parts:
                in_csw = False
                continue

            if "cswch/s" in parts:
                in_csw = True
                col_map = {name: idx for idx, name in enumerate(parts)}

                if "cswch/s" in col_map and "nvcswch/s" in col_map:
                    col_vol_idx = col_map["cswch/s"]
                    col_invol_idx = col_map["nvcswch/s"]

                    if "TID" in col_map:
                        col_tid_idx = col_map["TID"]
                    elif "PID" in col_map:
                        col_tid_idx = col_map["PID"]

                    if "Command" in col_map:
                        col_cmd_idx = col_map["Command"]
                continue

            if in_csw and len(parts) > max(col_vol_idx, col_invol_idx):
                timestamp = parts[0]

                if timestamp == "Average:" or ":" not in timestamp:
                    continue

                try:
                    vol_val = float(parts[col_vol_idx])
                    invol_val = float(parts[col_invol_idx])

                    per_second[timestamp]['vol'] += vol_val
                    per_second[timestamp]['invol'] += invol_val

                    if col_tid_idx != -1 and len(parts) > col_tid_idx:
                        tid = parts[col_tid_idx]

                        if tid != "-":
                            per_thread[tid]['vol'] += vol_val
                            per_thread[tid]['invol'] += invol_val
                            per_thread[tid]['samples'] += 1

                            if col_cmd_idx != -1 and len(parts) > col_cmd_idx:
                                per_thread[tid]['cmd'] = parts[col_cmd_idx]

                except ValueError:
                    continue

    if not per_second:
        print("Error: No valid context switch data (cswch/s) found in this file.\n")
        return

    # --- DISPLAY PER-SECOND ---
    print("\n[1] PER-SECOND SUMMARIES (Total sum of all recorded tasks)")
    sec_table = PrettyTable()
    sec_table.field_names = ["Time", "Voluntary (cswch/s)", "Involuntary (nvcswch/s)"]
    sec_table.align["Time"] = "l"
    sec_table.align["Voluntary (cswch/s)"] = "r"
    sec_table.align["Involuntary (nvcswch/s)"] = "r"

    for ts, data in per_second.items():
        sec_table.add_row([ts, f"{data['vol']:.2f}", f"{data['invol']:.2f}"])

    print(sec_table)

    # --- DISPLAY PER-THREAD ---
    print("\n[2] PER-THREAD AVERAGES (Average rates over active intervals)")
    thd_table = PrettyTable()
    thd_table.field_names = ["TID/PID", "Command", "Avg Vol/s", "Avg Invol/s", "Samples"]
    thd_table.align["TID/PID"] = "l"
    thd_table.align["Command"] = "l"
    thd_table.align["Avg Vol/s"] = "r"
    thd_table.align["Avg Invol/s"] = "r"
    thd_table.align["Samples"] = "r"

    # Sort threads by highest average INVOLUNTARY context switches
    sorted_threads = sorted(per_thread.items(), key=lambda x: x[1]['invol'] / x[1]['samples'], reverse=True)

    for tid, data in sorted_threads:
        avg_vol = data['vol'] / data['samples']
        avg_invol = data['invol'] / data['samples']
        cmd = data['cmd']
        thd_table.add_row([tid, cmd, f"{avg_vol:.2f}", f"{avg_invol:.2f}", data['samples']])

    print(thd_table)

    # --- DISPLAY OVERALL SUMMARY ---
    num_seconds = len(per_second)
    total_vol_overall = sum(d['vol'] for d in per_second.values())
    total_invol_overall = sum(d['invol'] for d in per_second.values())

    print("\n[3] OVERALL SYSTEM-WIDE SUMMARY")
    sum_table = PrettyTable()
    sum_table.header = False
    sum_table.align = "l"
    sum_table.add_row(["Total Intervals (Seconds) Analyzed:", num_seconds])
    sum_table.add_row(["Total Unique Threads Recorded:", len(per_thread)])
    sum_table.add_row(["System-Wide Avg Voluntary/sec:", f"{total_vol_overall / num_seconds:.2f}"])
    sum_table.add_row(["System-Wide Avg Involuntary/sec:", f"{total_invol_overall / num_seconds:.2f}"])

    print(sum_table)
    print("")

if __name__ == "__main__":
    if 1 == 2:
        if len(sys.argv) < 2:
            print(f"Usage: python3 {sys.argv[0]} <pidstat-log-1> [<pidstat-log-2> ...]")
            sys.exit(1)

    root_folder = '/home/dlovison/Downloads/240'

    # Process all files passed as arguments
    for logfile in [f'{root_folder}/pidstat-quarkus3-jvm-0-steady-state.log', f'{root_folder}/pidstat-spring4-jvm-0-steady-state.log']:
        analyze_pidstat_log(logfile)
