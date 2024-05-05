package com.project.isi.crp.aws

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest
import com.amazonaws.services.s3.model.ListObjectsV2Request
import com.amazonaws.services.s3.model.ListObjectsV2Result
import org.springframework.stereotype.Service
import java.net.URL
import java.util.*
import java.util.concurrent.TimeUnit

@Service
class S3Service(private val s3Client: AmazonS3) {

    fun fetchAllObjectsFromS3(bucketName: String, prefix: String? = null): List<String> {
        val listObjectsRequest = ListObjectsV2Request()
            .withBucketName(bucketName)
            .withPrefix(prefix)

        val listObjectsResult: ListObjectsV2Result = s3Client.listObjectsV2(listObjectsRequest)

        return listObjectsResult.objectSummaries.map { it.key }
    }

    fun generatePreSignedUrlForObject(bucketName: String, objectKey: String, expirationMinutes: Long = 15): String? {
        val expiration = Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(expirationMinutes))

        val generatePresignedUrlRequest = GeneratePresignedUrlRequest(bucketName, objectKey)
            .withExpiration(expiration)

        return try {
            val preSignedUrl: URL = s3Client.generatePresignedUrl(generatePresignedUrlRequest)
            preSignedUrl.toString()
        } catch (e: Exception) {
            // If an exception occurs, return null
            null
        }
    }
}