# Benchmark

## DefaultBenchmark

📝 Benchmark must be run on a real device.

## BaselineProfileGenerator

📝 Baseline Profile Collection requires a rooted device, and a rooted adb session. Use `adb root`.

… but can be tested on an emulator after applying:

```diff
 android {
     defaultConfig {
-         // testInstrumentationRunnerArguments["androidx.benchmark.suppressErrors"] = "EMULATOR"
+         testInstrumentationRunnerArguments["androidx.benchmark.suppressErrors"] = "EMULATOR"
     }
 }
```
