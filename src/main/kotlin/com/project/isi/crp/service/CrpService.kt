package com.project.isi.crp.service

import com.project.isi.crp.aws.S3Service
import com.project.isi.crp.model.car
import org.springframework.stereotype.Service

@Service
class CrpService(private val s3Service: S3Service) {

    fun testService(): String{
        return "running...."
    }

    fun cars() : List<String?>{
        return try {
            fetchPreSignedUrls()
        } catch (e: Exception) {
            emptyList()
        }
    }

    private fun fetchPreSignedUrls(): List<String?> {
        val bucketName = "isi-test-cars"
        val prefix = "cars"
        val expirationMinutes : Long = 15

        val objectKeys = s3Service.fetchAllObjectsFromS3(bucketName, prefix)

        return objectKeys.map { objectKey ->
            s3Service.generatePreSignedUrlForObject(bucketName, objectKey, expirationMinutes)
        }
    }
}