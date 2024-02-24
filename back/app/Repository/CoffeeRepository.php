<?php

namespace App\Repository;

use App\Models\Coffee;
use Illuminate\Support\Facades\Storage;
use Exception;

class CoffeeRepository implements RepositoryInterface
{
    /**
     * @param array $attributes
     * @param null $model
     * @return mixed
     * @throws Exception
     */
    public function store(array $attributes, $model = null): mixed
    {
        $image = $attributes['image'] ?? null;
        $imageName = $image ? $image->hashName() : null;
        $attributes['image'] = $imageName;

        $coffee = Coffee::create($attributes);
        if ($coffee && $image) {
            Storage::disk('public')->put($imageName, file_get_contents($image));
        }
        return $coffee;
    }

    /**
     * @throws Exception
     */
    public function update($attributes, $coffee): mixed
    {
        $newImage = $attributes['image'] ?? null;
        $newImageName = null;
        $oldImageName = $coffee->image;
        if ($newImage) {
            $newImageName = $newImage->getClientOriginalName() !== $oldImageName ?
                $newImage->hashName() : $coffee->image;
        }
        $attributes['image'] = $newImageName;
        $coffee->update($attributes);
        if ($newImage) {
            if ($coffee->image) {
                Storage::disk('public')->delete($oldImageName);
            }
            Storage::disk('public')->put($newImageName, file_get_contents($newImage));

        }
        if (is_null($newImageName) && $oldImageName !== null) {
            Storage::disk('public')->delete($oldImageName);
        }
        return $coffee;
    }

    public function delete($coffee)
    {
        return $coffee->delete();
    }
}
