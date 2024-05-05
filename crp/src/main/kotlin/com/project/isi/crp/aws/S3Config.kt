package com.project.isi.crp.aws

import com.amazonaws.auth.AWSCredentialsProvider
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class S3Config {
    @Bean
    fun s3Client(): AmazonS3 {

        val credentialsProvider: AWSCredentialsProvider = DefaultAWSCredentialsProviderChain()

        val client = AmazonS3ClientBuilder
            .standard()
            .withRegion(Regions.EU_CENTRAL_1)
            .withCredentials(credentialsProvider)

        // here we can check deployment environment (e.g., local, dev,prod)
        // based on the environment we can use different credential mechanism
        return client.build()
    }


}