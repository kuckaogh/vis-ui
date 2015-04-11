package com.kotcrab.vis.plugin.runtime.spine;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.spine.Skeleton;
import com.esotericsoftware.spine.SkeletonData;
import com.kotcrab.vis.plugin.runtime.spine.SkeletonDataLoader.SkeletonDataLoaderParameter;
import com.kotcrab.vis.runtime.api.plugin.RuntimeEntitySupport;

public class SpineRuntimeSupport implements RuntimeEntitySupport<SpineData, SpineEntity> {
	@Override
	public void setLoaders (AssetManager manager) {
		manager.setLoader(SkeletonData.class, new SkeletonDataLoader());
	}

	@Override
	public Class<SpineEntity> getEntityClass () {
		return SpineEntity.class;
	}

	@Override
	public void resolveDependencies (Array<AssetDescriptor> deps, SpineData entityData) {
		SkeletonDataLoaderParameter parameter = new SkeletonDataLoaderParameter(entityData.atlasPath, entityData.scale);
		deps.add(new AssetDescriptor(entityData.skeletonPath, SkeletonData.class));
	}

	@Override
	public SpineEntity getInstanceFromData (AssetManager manager, SpineData data) {
		SkeletonData skeletonData = manager.get(data.skeletonPath, SkeletonData.class);
		SpineEntity entity = new SpineEntity(data.id, data.atlasPath, data.skeletonPath, new Skeleton(skeletonData));
		data.loadTo(entity);
		return entity;
	}
}
