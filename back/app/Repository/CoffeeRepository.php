<?php

namespace App\Repository;

use App\Models\Coffee;
use Exception;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Storage;

class CoffeeRepository implements RepositoryInterface
{
    /**
     * @param array $attributes
     * @param null $model
     * @throws Exception
     */
    public function store($attributes, $model = null)
    {
        try {
            DB::beginTransaction();
            $image = $attributes['image'];
            $imageName = $image->hashName();
            Storage::disk('public')->put($imageName, file_get_contents($image));
            $coffee = Coffee::create([
                'type' => data_get($attributes, 'type'),
                'price' => data_get($attributes, 'price'),
                'image' => $imageName,
                'brew_time' => data_get($attributes, 'brew_time'),
                'coffee_amount' => data_get($attributes, 'coffee_amount')
            ]);
            if (!$coffee) {
                throw new Exception('Error while creating coffee');
            }
            DB::commit();
            return $coffee;
        } catch (Exception $e) {
            DB::rollback();
            return $e;
        }

    }

    public function update($attributes, $coffee)
    {
        try {
            DB::beginTransaction();
            $image = $attributes['image'];
            $imageName = $image->hashName();
            Storage::disk('public')->delete($coffee->image);
            Storage::disk('public')->put($imageName, file_get_contents($image));
            $coffee = $coffee->update([
                'type' => data_get($attributes, 'type'),
                'price' => data_get($attributes, 'price'),
                'image' => $imageName,
                'brew_time' => data_get($attributes, 'brew_time'),
                'coffee_amount' => data_get($attributes, 'coffee_amount')
            ]);
            if (!$coffee) {
                throw new Exception('Error while updating coffee');
            }
            DB::commit();
            return $coffee;
        } catch (Exception $e) {
            DB::rollback();
            return $e;
        }
    }

    public function delete($coffee)
    {
        try {
            DB::beginTransaction();
            $coffee->delete();
            DB::commit();
            return $coffee;
        } catch (Exception $e) {
            DB::rollBack();
            return $e;
        }
    }
}
