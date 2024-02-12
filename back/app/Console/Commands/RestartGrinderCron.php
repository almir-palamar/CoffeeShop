<?php

namespace App\Console\Commands;

use Illuminate\Console\Command;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Log;

class RestartGrinderCron extends Command
{
    /**
     * The name and signature of the console command.
     *
     * @var string
     */
    protected $signature = 'grinder:restart';

    /**
     * The console command description.
     *
     * @vqar string
     */
    protected $description = 'Restart espresso machine grinder coffee amount to default';

    /**
     * Execute the console command.
     *
     * @return int
     */
    public function handle(): int
    {
        try {
            DB::table('espresso_machines')->update([
               'grinder' => config('constants.GRINDER_CAPACITY')
            ]);
            Log::info("Grinder restarted");
        } catch (\Exception $e) {
            Log::error("Grinder reset: " . $e->getMessage());
            echo $e->getMessage();
        }
    }
}
