<?php

namespace App\Repositories;

interface RepositoryInterface {

    public function store(array $attributes, $model = null);

    public function update(array $attributes, $model);

    public function delete($model);

}
